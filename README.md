# Procurement Living Application

## About
This is an official Living Application example for Bonita BPM 7.0

This example demonstrates the following concepts:
- Living Application
- Forms, pages and custom widgets built the UI Designer
- Process using BDM and contracts

## Installation

1. Download the <a href="https://github.com/Bonitasoft-Community/procurement-example/releases">application bundle</a>
2. Extract the bundle to a temporary folder
3. Import the .bos file in your Bonita BPM Studio (it contains 3 processes: Procurement request, Init sample procurement data and Create supplier)
4. Deploy all 3 processes (click on Run in a Studio but do not complete the forms)
4. Run the sample data initialization process once and remove it from the Portal
6. In the Portal, as an administrator, go to 'Resources' and add the 2 custom pages .zip files. You can read more about custom pages <a href="http://documentation.bonitasoft.com/pages">here</a>.
7. In the Portal, go to 'Applications' and import the Procurement application.xml file. You can read more about applications <a href="http://documentation.bonitasoft.com/applications-0">here</a>.
8. Run the Procurement application by accessing to this url (the port number may vary):<br/><a href="http://localhost:8080/bonita/apps/procurement">http://localhost:8080/bonita/apps/procurement</a>


**Note:** this simplified version of the Procurement Request process only uses one actor.<br/>
The same user can fill a procurement request and approve it himself.

## Screenshots
#### Procurement Living Application
<img src="/screenshots/request-listing.png?raw=true" alt="Procurement Living Application"/>

#### Procurement Request process - Diagram
<img src="/screenshots/procurement-request-diagram.png?raw=true" alt="Procurement Request process - Diagram"/>

#### Procurement Request process - Fill request form
<img src="/screenshots/fill-request-form.png?raw=true" alt="Procurement Request process - Fill request form"/>

#### Procurement Request process - Review request form
<img src="/screenshots/review-request-form.png?raw=true" alt="Procurement Request process - Review request form"/>
