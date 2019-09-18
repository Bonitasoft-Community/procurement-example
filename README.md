# Procurement Living Application

## About
This is an official Living Application example for Bonita 7.
It is build using Bonita 7.9.3 and is compatible with any future releases of Bonita 7.

This example demonstrates the following concepts:
- Living Application
- Forms, pages and custom widgets built in the UI Designer
- Process using BDM and contracts

## Installation

1. Download the <a href="https://github.com/Bonitasoft-Community/procurement-example/releases">project .bos file</a>
1. Import the .bos file in your Bonita Studio (it contains 3 processes: Procurement request, Init sample procurement data and Create supplier)
1. Deploy all 3 processes: select the process in the **project explorer**, do a right click and select **Deploy**
1. Run the sample data initialization process once and remove it from the Portal (using administration view)
1. Deploy the two application pages: in the **project explorer**, select **Pages/Forms/Layouts**, select a page, do a right click and select **Deploy**. Do the same for the second page. You can read more about pages from the <a href="https://documentation.bonitasoft.com/bonita/7.9/pages">official documentation</a>.
1. Deploy the application: in the **project explorer**, select **Application descriptors**, select _Procurement_application.xml_, do a right click and select **Deploy**. You can read more about applications in the <a href="https://documentation.bonitasoft.com/bonita/7.9/applications">official documentation</a>.
10. Run the Procurement application by accessing to this URL (the port number may vary):<br/><a href="http://localhost:8080/bonita/apps/procurement">http://localhost:8080/bonita/apps/procurement</a>


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
