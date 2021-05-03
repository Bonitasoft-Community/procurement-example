# Procurement Living Application

## About
This is an official Living Application example for Bonita Community and Enterprise Editions.
The latest release is built using Bonita 2021.1 and is compatible with any future releases of Bonita.

:::info
*If you are using an older version of Bonita, download the release corresponding to the version in question.*
:::

This example demonstrates the following concepts:
- Living Application
- Forms, pages and custom widgets built in the UI Designer
- Process using BDM and contracts
- Variable initialization using BonitaUsers templates

This example contains 3 process diagrams:

- *Init sample procurement data*: The purpose of this process is to create a bunch of suppliers. The process declares one business variable `suppliers` that is used to create a list of suppliers. Suppliers to create are defined in the business variable default value. Executing this process will create the suppliers.
	:warning: This process should not be executed twice to avoid suppliers duplication (The process instantiation form includes a warning message if suppliers already exists).
- *Procurement request*: This is the main process diagram. A user fill a procurement request and identify potential suppliers. This request is sent to those suppliers for quotation. After completed, the quotations are sent back to the requestor for review and selection.
- *Create supplier*: This additional process does not have any tasks. It is used to automatically create a new supplier based on the data provided by the user in the instantiation form.

#### Procurement Request process - Diagram
<img src="/screenshots/procurement-request-diagram.png?raw=true" alt="Procurement Request process - Diagram"/>


## Installation

1. Download the <a href="https://github.com/Bonitasoft-Community/procurement-example/releases">project .bos file</a>
1. Import the .bos file in your Bonita Studio. 
1. Deploy all 3 processes: select the process in the **project explorer**, do a right click and select **Deploy**
1. Run the sample data initialization process once and remove it from the Portal (using administration view)
1. Deploy the two application pages: in the **project explorer**, select **Pages/Forms/Layouts**, select a page, do a right click and select **Deploy**. Do the same for the second page. You can read more about pages from the <a href="https://documentation.bonitasoft.com/bonita/7.9/pages">official documentation</a>.
1. Deploy the application: in the **project explorer**, select **Application descriptors**, select _Procurement_application.xml_, do a right click and select **Deploy**. You can read more about applications in the <a href="https://documentation.bonitasoft.com/bonita/7.9/applications">official documentation</a>.
10. Run the Procurement application by accessing to this URL (the port number may vary):<br/><a href="http://localhost:8080/bonita/apps/procurement">http://localhost:8080/bonita/apps/procurement</a>


:::info
**Note:** this simplified version of the Procurement Request process only uses one actor.<br/>
The same user can fill a procurement request and approve it himself.
:::

## Screenshots
#### Procurement Living Application
<img src="/screenshots/request-listing.png?raw=true" alt="Procurement Living Application"/>

#### Procurement Request process - Fill request form
<img src="/screenshots/fill-request-form.png?raw=true" alt="Procurement Request process - Fill request form"/>

#### Procurement Request process - Review request form
<img src="/screenshots/review-request-form.png?raw=true" alt="Procurement Request process - Review request form"/>
