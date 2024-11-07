import requests
import json
from requests.auth import HTTPBasicAuth

# RESTCONF server details
BASE_URL = "https://192.168.1.1/restconf/data"  # Replace with your device's IP and RESTCONF base path
USERNAME = "admin"  # Replace with your username
PASSWORD = "admin"  # Replace with your password

# Headers for RESTCONF requests
HEADERS = {
    "Content-Type": "application/yang-data+json",
    "Accept": "application/yang-data+json"
}

# Suppress SSL warnings (for self-signed certificates)
requests.packages.urllib3.disable_warnings()


def get_interfaces():
    """Performs a GET request to retrieve interface data."""
    url = f"{BASE_URL}/ietf-interfaces:interfaces"
    response = requests.get(url, headers=HEADERS, auth=HTTPBasicAuth(USERNAME, PASSWORD), verify=False)
    if response.status_code == 200:
        print("GET Response:")
        print(json.dumps(response.json(), indent=2))
    else:
        print(f"GET request failed with status {response.status_code}: {response.text}")


def create_interface(interface_name, description="Configured via RESTCONF"):
    """Performs a POST request to create a new interface."""
    url = f"{BASE_URL}/ietf-interfaces:interfaces/interface={interface_name}"
    payload = {
        "ietf-interfaces:interface": {
            "name": interface_name,
            "description": description,
            "type": "iana-if-type:ethernetCsmacd",
            "enabled": True
        }
    }
    response = requests.put(url, headers=HEADERS, data=json.dumps(payload),
                            auth=HTTPBasicAuth(USERNAME, PASSWORD), verify=False)
    if response.status_code in (200, 201):
        print("POST Response: Interface created successfully.")
    else:
        print(f"POST request failed with status {response.status_code}: {response.text}")


def update_interface(interface_name, new_description):
    """Performs a PUT request to update an existing interface's description."""
    url = f"{BASE_URL}/ietf-interfaces:interfaces/interface={interface_name}"
    payload = {
        "ietf-interfaces:interface": {
            "name": interface_name,
            "description": new_description,
            "type": "iana-if-type:ethernetCsmacd",
            "enabled": True
        }
    }
    response = requests.put(url, headers=HEADERS, data=json.dumps(payload),
                            auth=HTTPBasicAuth(USERNAME, PASSWORD), verify=False)
    if response.status_code in (200, 204):
        print("PUT Response: Interface updated successfully.")
    else:
        print(f"PUT request failed with status {response.status_code}: {response.text}")


def delete_interface(interface_name):
    """Performs a DELETE request to remove an interface."""
    url = f"{BASE_URL}/ietf-interfaces:interfaces/interface={interface_name}"
    response = requests.delete(url, headers=HEADERS, auth=HTTPBasicAuth(USERNAME, PASSWORD), verify=False)
    if response.status_code == 204:
        print("DELETE Response: Interface deleted successfully.")
    else:
        print(f"DELETE request failed with status {response.status_code}: {response.text}")


def main():
    # GET interfaces
    print("Performing GET operation to retrieve interfaces:")
    get_interfaces()

    # POST (create) an interface
    print("\nPerforming POST operation to create an interface:")
    create_interface("GigabitEthernet2")

    # PUT (update) an interface
    print("\nPerforming PUT operation to update an interface description:")
    update_interface("GigabitEthernet2", "Updated via RESTCONF")

    # DELETE an interface
    print("\nPerforming DELETE operation to delete the interface:")
    delete_interface("GigabitEthernet2")


if __name__ == "__main__":
    main()
