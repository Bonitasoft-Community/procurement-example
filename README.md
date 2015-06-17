# Procurement Living Application
This is an official Living Application example for Bonita BPM 7.0

1. Download the <a href="https://github.com/Bonitasoft-Community/procurement-example/releases">application bundle</a>
2. Extract the bundle to a temporary folder
3. Import the .bos file in your Bonita BPM Studio (it contains 3 processes: Procurement request, Init sample procurement data and Create supplier)
4. Deploy all 3 processes (click on Run in a Studio but do not complete the forms)
4. Run the sample data initialization process once and remove it from the Portal
6. Import the 2 custom pages .zip files in your Portal
7. Import the Procurement application .xml file in your Portal
8. Run the Procurement application by accessing to this url (the port number may vary):<br/><a href="http://localhost:8080/bonita/apps/procurement">http://localhost:8080/bonita/apps/procurement</a>


**Note:** this simplified version of the Procurement Request process only uses one actor.<br/>
The same user can fill a procurement request and and approve it himself.

## Screenshots
#### Process Diagram
<img src="/screenshots/diagram.png?raw=true" alt="Process diagram"/>
