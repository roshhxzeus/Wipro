import os
import subprocess
from ncclient import manager
from ncclient.operations.rpc import RPCError
from lxml import etree

# YANG Validation Function
def validate_yang_model(yang_file_path):
    print("Validating YANG model...")
    try:
        result = subprocess.run(['pyang', '-p', os.path.dirname(yang_file_path), '-f', 'tree', yang_file_path],
                                capture_output=True, text=True, check=True)
        print("YANG Model Validation Successful.")
        print(result.stdout)
        return True
    except subprocess.CalledProcessError as e:
        print("YANG Model Validation Failed.")
        print(e.stderr)
        return False

# NETCONF Operations
def get_netconf_capabilities(mgr):
    print("\nAvailable NETCONF Capabilities:")
    for capability in mgr.server_capabilities:
        print(capability)

def get_config(mgr, filter_criteria):
    print("\nFetching Configuration...")
    try:
        config = mgr.get_config(source='running', filter=('subtree', filter_criteria)).data_xml
        print("Configuration Retrieved:")
        print(config)
        return config
    except RPCError as e:
        print(f"Failed to fetch configuration: {e}")
        return None

def edit_config(mgr, config_xml):
    print("\nApplying Configuration Change...")
    try:
        mgr.edit_config(target="running", config=config_xml)
        print("Configuration Updated Successfully.")
    except RPCError as e:
        print(f"Failed to edit configuration: {e}")

def main():
    # Step 1: Validate YANG Model
    yang_model_path = "ietf-interfaces.yang"  # Path to your YANG model file
    if not validate_yang_model(yang_model_path):
        return

    # Step 2: Connect to NETCONF Server
    print("\nConnecting to NETCONF server...")
    with manager.connect(
        host="192.168.1.1",  # Replace with your NETCONF device IP
        port=830,
        username="admin",
        password="admin",
        hostkey_verify=False
    ) as mgr:
        print("NETCONF connection established.")

        # Display NETCONF Capabilities
        get_netconf_capabilities(mgr)

        # Step 3: Retrieve Configuration Using NETCONF
        interface_filter = """
            <interfaces xmlns="urn:ietf:params:xml:ns:yang:ietf-interfaces">
                <interface>
                    <name>GigabitEthernet1</name>
                </interface>
            </interfaces>
        """
        config = get_config(mgr, interface_filter)

        # Step 4: Edit Configuration
        new_config = """
            <config>
                <interfaces xmlns="urn:ietf:params:xml:ns:yang:ietf-interfaces">
                    <interface>
                        <name>GigabitEthernet1</name>
                        <description>Configured via NETCONF</description>
                        <enabled>true</enabled>
                    </interface>
                </interfaces>
            </config>
        """
        edit_config(mgr, new_config)

        # Step 5: Verify Configuration Change
        config_after_edit = get_config(mgr, interface_filter)
        print("\nConfiguration after edit:")
        print(config_after_edit)

if __name__ == "__main__":
    main()
