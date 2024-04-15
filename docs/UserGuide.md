---
layout: page
title: HouseKeeping Hub User Guide
---

Hello there! A very warm welcome to HouseKeeping Hub, the premier desktop application tailored for the **administrative staff
of housekeeping companies** in Singapore.

HouseKeeping Hub boasts specialised and robust functionality, including contact management tools for clients and housekeepers as well as a booking management system,
our application ensures that the admin can effectively **manage an extensive contact list** and efficiently **match suitable housekeepers
to clients** when on-call.

That's not all! Our application also offers a special feature: instantly retrieve a client call list
that contains clients whose estimated next cleaning dates are nearing - a feature sure to increase customer retention rates.

With the efficiency of a **Command Line Interface ([CLI](#cli))** and the convenience of a **Graphical User Interface ([GUI](#gui))**, 
HouseKeeping Hub ensures fast and user-friendly usage for every admin!

Are you ready to revolutionize your administrative workflow? Read on to learn more!

--------------------------------------------------------------------------------------------------------------------

## Table of Contents
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Our Target User

HouseKeeping Hub is specially tailored for a housekeeping company's admin whose role could involve:
1. Managing a contact list of clients and housekeepers detailing their personal particulars
2. Maintaining other important information regarding clients, including their booking dates or preferred interval between housekeeping services
3. Maintaining booking information and availability of every housekeeper
4. Searching for suitable housekeepers for a client, based on certain criteria like availability or area
5. Calling and reminding clients who have yet to make a new booking after a certain amount of time to do so

Our application is perfectly optimised to support the admin in accomplishing these tasks **effectively** and **with ease**!

Furthermore, our application is suitable for admins of all diverse roles or backgrounds. Even admins who are **not
tech-savvy**, or who have **limited administrative experience**, will find it a breeze to learn how to use HouseKeeping Hub -
it's that intuitive and simple!

Though, as our application utilizes a **Command Line Interface ([CLI](#cli))**, it is naturally better optimised for
those who prefer typing to mouse actions.

Now that we have fleshed out our target user, we do recognise that **you**, the reader, might not be here as the user
of our application!
If you are here as...
1. A housekeeping company's admin learning how to use the application, we have provided detailed information on how to get started down below - we wish you all the best!
2. A housekeeping company's upper management, looking to implement our software into your company's workflow system, do reach out to us so that we may offer you the best service!
3. A fellow developer, we welcome you to explore our application and share with us your valued feedback!

Following this, we will delve into the purpose of this user guide.

--------------------------------------------------------------------------------------------------------------------

## Purpose of this guide

This guide has been created to help you understand and utilize the features and functionalities of our software
 effectively. Whether you're a new user who is not familiar with command line interface ([CLI](#cli)) or an expert looking to enhance 
your skills, this guide aims to provide you with the information you need to make the most of our application!

In this guide, you will find a quick start (guide to install and start using our product), a list of features and how to use them, and 
a glossary to help you understand any technical jargon. This guide is designed to provide you with clear and concise instructions 
in a reader-friendly format to enhance your experience in using our application. Below is the table of contents for quick navigation
of our guide.

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## How to use this guide:question:

Let's get started! For users who are familiar with our application, you may want to skip straight to the
[command summary](#command-summary) for a quick refresh of available commands.

For beginner users who are learning how to use this guide and our application, no worries!
Here's a rundown of every section in this guide:

If you have yet to install HouseKeeping Hub, you can refer to the [installation instructions](#installation).

After installing the application, you can start off by following our [tutorial](#tutorial) to familiarise yourself
with the basic features of the app.

The [features](#features) section provides the detailed overview of each command, command formats and examples.

If you'd like a quick reference of all available commands, check out the [command summary](#command-summary).

You can head over to the [Frequently Asked Questions](#faq) section to view answers to common queries regarding the app.

Finally, we have the [glossary](#glossary) section to clarify any technical jargon used.

Now you have successfully mastered how to use this guide! One last thing: learn how to **navigate the guide**
in the section below.

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Navigating our guide :mag:

This section is to aid you in navigating our guide.

Words highlighted in blue in our guide can direct you to:
1. A specific section in our guide ([FAQ](#faq))
2. An external link ([download link](https://github.com/AY2324S2-CS2103T-W09-1/tp/releases)), or
3. An explanation of a term in the glossary ([CLI](#cli)).

To return to the table of contents, you may click on this button, which appears at the end of every section:
[:arrow_up_small:](#table-of-contents)

Tips that can enhance your understanding of our features and/or improve our application's usability will be highlighted
in these boxes:
<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
These are tips to help you use the application more effectively!
</div>

Extra information about certain features will be included in these boxes:
<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>
Additional information specified here!
</div>

Warnings that caution you on potentially adverse or unintended effects will be accentuated in these boxes:
<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
**Very** important information here! Please read me!
</div>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Installation :computer:
<a id="installation"></a>

1. This application requires `Java 11` or above to be installed in your Computer. You can download `Java 11` from [here](https://www.oracle.com/sg/java/technologies/javase/jdk11-archive-downloads.html).

1. Download the latest `HousekeepingHub-v1.3.1.jar` from [here](https://github.com/AY2324S2-CS2103T-W09-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your HouseKeeping Hub.

1. Open a [command terminal](#terminal), and type in `cd [folder_name]` to navigate into the folder you placed the [jar](#jar) file in.

1. Type in `java -jar HousekeepingHub-v1.3.1.jar` to run the application.<br>

   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data for demonstration purpose.
   Once you are familiar with the app, you can use the `clear` command to remove all the sample data.
   <div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
   The `clear` command is irreversible! Please use it only when certain.
   </div>
   <br><br>


      ![Ui](./images/Ui.png)

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Interpreting the Graphical User Interface (GUI)

HouseKeeping Hub provides a clean user interface that elegantly displays all its information.

Component | Description                                                                                                                                       
--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Command Box | Type and enter your commands here
Result Box | The outcomes of your executed commands will be displayed here, including success and error messages
Client List | List all bookings of a housekeeper with `list`<br>Format: `booking housekeeper list INDEX`<br>Example: `booking housekeeper list 2`
Housekeeper List | List all bookings of a housekeeper with `list`<br>Format: `booking housekeeper list INDEX`<br>Example: `booking housekeeper list 2`

In the client list, each client card will include the client's details and their housekeeping details.

Component | Description                                                                                                                                       
--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Client Details | The client's particulars are displayed here
Housekeeping Details | The client's housekeeping details are displayed here

--------------------------------------------------------------------------------------------------------------------

## Tutorial
<a id="tutorial"></a>

After you have launched HouseKeeping Hub, you should see some sample data for you to try out the features.

To use a command, type the command into the command box and press Enter to execute it.

Let us try some simple workflows!

Adding and deleting contacts:

1. Add a client named 'Melissa Tan' into the client list:<br>
   `add client n/Melissa Tan p/98362547 e/mtan23@example.com a/Jane street, block 321 ar/northeast`
2. Delete the 3rd housekeeper that appears in the housekeeper list:<br>
   `delete housekeeper 3`

Finding and listing contacts:

1. Find clients with the name 'David':<br>
   `find client n/david`
2. List all clients again:<br>
   `list client`

Adding a booking for a housekeeper:

1. Search for housekeepers who are available in the East, on 2024-03-02 pm:<br>
   `booking housekeeper search east 2024-03-02 pm`
2. View the booking list of the 2nd housekeeper that appears in the housekeeper list:<br>
   `booking housekeeper list 2`<br>
   The list should be empty. 
3. Add a new booking for the 2nd housekeeper:<br>
   `booking housekeeper add 2 2024-05-22 pm`
4. View the booking list of the 2nd housekeeper again:<br>
   `booking housekeeper list 2`<br>
   The list should contain the new booking you just made. 
5. Delete the booking you just made:<br>
   `booking housekeeper delete 2 1`

Edit a client's housekeeping details:

1. Add a booking date for the 2nd client that appears in the client list:<br>
   `booking client add 2 2024-03-11 pm`
2. Edit the booking date for the 2nd client:<br>
   `booking client edit 2 bd/2024-03-17 am`
3. Remove the 2nd client's housekeeping details:<br>
   `booking client remove 2`
4. Set the 2nd client's housekeeping details again:<br>
   `booking client set 2 2024-03-01 15 months`

Once you've familiarized yourself with the commands, you can clear all the sample data in the application with the
`clear` command.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
The `clear` command is irreversible! Please use it only when certain.
</div>

Congratulations on completing the tutorial! For more in depth explanations of each command, you may refer to the [Features](#features) section below.

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Features
<a id="features"></a>

Welcome to the features section! Here, we will provide detailed information on how to use each feature in the application.
The information box below contains pertinent information to help you interpret the command formats correctly.

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* `NAME` is case-sensitive and character-sensitive.<br>
  e.g. `John Doe` and `john doe` is different person (not considered as duplicate).

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines 
as space characters surrounding line-breaks may be omitted when copied over to the application, leading to failed commands.
</div>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](./images/helpMessage.png)

Format: `help`

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

### Adding a person: `add`

Adds a client or housekeeper to HouseKeeping Hub.

Format: `add TYPE n/NAME e/EMAIL p/PHONE_NUMBER a/ADDRESS ar/AREA [d/DETAILS] [t/TAG]…​`

Notes: 
* `TYPE` can be either 'client' or 'housekeeper'.
* `AREA` can be either 'east', 'southeast', 'south', 'southwest', 'west', 'northwest', 'north', or 'northeast'.
* `DETAILS` is optional and refers to the housekeeping details for CLIENT ONLY. It is not applicable for housekeepers.
The format for `DETAILS` is `d/yyyy-MM-dd NUMBER INTERVAL` where `yyyy-MM-dd` is the date of the last
housekeeping, `NUMBER` is the (non-negative) quantity of `INTERVAL`(s) which can be ***'days', 'weeks', 'months' or 'years'.*** 
This `INTERVAL` is the period between housekeeping sessions that the client prefers. It is meant to be an estimate, so
options such as `2 weeks and 3 days` are not supported. If precision is needed, you should convert it to `NUMBER days`.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Names of clients/housekeepers should be **UNIQUE**. You may not add more than one client/housekeeper with the exact
same name. For example, you may have both a client and a housekeeper named `Elon Tan`, but not 2 clients named
`Elon Tan`.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Both client and housekeeper can have only one name, email, phone number, address and area, but any number of tags 
(including 0).
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A client can have at most one set of housekeeping details.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Housekeeping details of the client can be modified using the `booking` command. Without housekeeping details,
the customer is assumed to not want notifications for housekeeping. Therefore, `leads` will not include clients without housekeeping details.
To set or remove housekeeping details after initiation, refer to `set` and `remove` under `booking` below.
</div>

Examples:
* `add client n/Elon e/elon@gmail.com p/088888888 a/Elon Street, Block 123, 101010 Singapore ar/west`
* `add housekeeper n/Betsy Crowe p/441234567 e/betsycrowe@example.com a/Newgate Prison t/criminal t/famous ar/south`

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

### Listing all persons : `list`

Returns the originally unfiltered list of the given type.

Format: `list TYPE`

Notes: 
* `TYPE` can only be either 'client' or 'housekeeper'

Example:
* `list client`
* `list housekeeper`

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

### Deleting a person : `delete`

Deletes the specified client or housekeeper from HouseKeeping Hub.

Format: `delete TYPE INDEX`

Notes:
* `TYPE` can be either 'client' or 'housekeeper'.
* Deletes the client or housekeeper at the specified `INDEX`.
* The index refers to the index number shown in the displayed list.
* The index **must be a positive integer** 1, 2, 3, …​

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The index to delete will work for any displayed list. i.e. What you see is what you get.
</div>

Examples:
* `list client` followed by `delete client 2` deletes the 2nd person in the client list.
* `list housekeeper` followed by `delete housekeeper 1` deletes the 1st person in the housekeeper list.

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------
### Editing a person : `edit`

Edits an existing person in the HouseKeeping Hub.

Format: `edit TYPE INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [ar/AREA] [t/TAG]…​`

Notes:
* `TYPE` can be either 'client' or 'housekeeper'.
* `AREA` can be either 'east', 'southeast', 'south', 'southwest', 'west', 'northwest', 'north', or 'northeast'.
* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit client 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st client to be `91234567` and `johndoe@example.com` respectively.

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

### Locating persons by keywords: `find`

Finds client or housekeeper whose names, address, or area contain any of the given keywords.

Format: `find TYPE n/KEYWORD [MORE_KEYWORDS] ar/KEYWORD [MORE_KEYWORDS] a/KEYWORD [MORE_KEYWORDS]`

Notes:
* `TYPE` can be either 'client' or 'housekeeper'.
* `AREA` can be either 'east', 'southeast', 'south', 'southwest', 'west', 'northwest', 'north', or 'northeast'.
* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find client n/John` returns `john` and `John Doe`
* `find housekeeper n/alex david ar/west` returns housekeeper which name is alex or david and cover the service of west area<br>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------


### Getting client call list: `leads`
Generates a list of leads by sorting ALL clients based on the predicted next time of housekeeping.
(There is no way to use the find feature with leads)
Clients with predicted next housekeeping date which is in the future will not be included.

Format: `leads`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The leads are sorted with the client with the earliest predicted next housekeeping date at the top. Housekeeping details are optional so clients without housekeeping details will not be included in the leads.
</div>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

### Booking commands: `booking`
The booking functionality in HouseKeeping Hub is a value added service that helps you to keep track of your clients' and housekeepers'
housekeeping schedules, allowing you to easily identify clients who might want to schedule another housekeeping appointment,
as well as efficiently match housekeepers to clients based on availability and area.

Here is a brief explanation of the commands before we delve into each one individually.

**For clients**, the `booking client` commands allow you to maintain every client's housekeeping details, which encompasses 4 attributes:
1. Last booking date (mandatory)
2. Preferred interval between each housekeeping (mandatory)
3. Next booking date (optional)
4. Deferment before next housekeeping date (optional)

It is optional to add housekeeping details for a client, in the event that they prefer not to be prompted to schedule
their housekeeping appointments. Naturally, these clients will not be included in the list after using the `leads` command.

There are 6 `booking client` commands available:
1. `set`: set last housekeeping date and preferred interval
2. `remove`: remove housekeeping details
3. `add`: add booking date
4. `delete`: delete booking date
5. `defer`: add deferment
6. `edit`: edit any of the 4 attributes

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
When a client has **no housekeeping details**, the commands `add`, `delete`, `edit` and `defer` **will not work**.<br>
In that case, first use the `set` command to add housekeeping details for the client.
</div>

**For housekeepers**, the `booking housekeeper` commands allow you to maintain every housekeeper's booking list and search
for housekeepers who are available at a specified date, time and area.

There are 4 `booking housekeeper` commands available:
1. `add`: add a booking to a housekeeper's booking list
2. `delete`: delete a booking from a housekeeper's booking list
3. `list`: list all bookings of a housekeeper
4. `search`: search for housekeepers available for a specified date, time and area

The **general format** for both `booking client` and `booking housekeeper` commands is as follows:

`booking TYPE ACTION INDEX [PARAMETERS]`, where:
1. TYPE: client or housekeeper
2. ACTION: specific command, such as `search` or `add`
3. INDEX: index of the target client/housekeeper, relative to the observed client/housekeeper list

Below are the detailed explanations for each command.

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

#### Booking commands for clients

--------------------------------------------------------------------------------------------------------------------

###### Setting last housekeeping date and preferred interval: `booking client set`

Sets the specified client's last housekeeping date and preferred interval.

Format: `booking client set INDEX DATE NUMBER INTERVAL`

Parameters:

`INDEX`: index of target client, relative to the observed client list
`DATE`: yyyy-MM-dd format
`NUMBER`: quantity of `INTERVAL`(s) - non-negative integer
`INTERVAL`: period between housekeeping sessions - 'days', 'weeks', 'months' or 'years'

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The specified index will work for any currently displayed list. i.e. What you see is what you get.<br>
The `INTERVAL` is the period between housekeeping sessions that the client prefers. It is meant to be an estimate, so
options such as `2 weeks and 3 days` are not supported. If precision is needed, you should convert it to `NUMBER days`.
</div>

Examples:
* `booking client set 2 2024-04-01 15 days` sets 2nd client's last housekeeping date and preferred interval as 2024-04-01 and 15 days respectively
* `booking client set 7 2024-05-16 2 months` sets 7th client's last housekeeping date and preferred interval as 2024-05-16 and 2 months respectively<br>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

###### Removing housekeeping details: `booking client remove`

Removes/deletes the specified client's housekeeping details (including 'last housekeeping date', 'preferred interval',
'booking date', and 'deferment').

Format: `booking client remove INDEX`

Parameters:

`INDEX`: index of target client, relative to the observed client list

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The specified index will work for any currently displayed list. i.e. What you see is what you get.
</div>

Examples:
* `booking client remove 3` removes the 3rd client's housekeeping details
* `booking client remove 1` removes the 1st client's housekeeping details<br>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

###### Adding booking date: `booking client add`

Adds a booking date for the specified client.

If the client has **no housekeeping details**, first use the `set` command to add housekeeping details for the client.

Format: `booking client add INDEX DATE TIME`

Parameters:

`INDEX`: index of target client, relative to the observed client list
`DATE`: yyyy-MM-dd format
`TIME`: am or pm

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The index to delete will work for any displayed list. i.e. What you see is what you get.<br>
`TIME` is categorised as either 'am' or 'pm' as housekeeping services often span a few hours,
and on account of housekeepers' travelling time between houses. Please look forward to future editions of our
application that support more flexible time frames!
</div>

Examples:
* `booking client add 3 2024-08-11 am` adds a booking for the 3rd client at 2024-08-11 am
* `booking client add 8 2024-02-18 pm` adds a booking for the 8th client at 2024-02-18 pm<br>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

###### Deleting booking date: `booking client delete`

Deletes the specified client's booking date.

If the client has **no housekeeping details**, first use the `set` command to add housekeeping details for the client.

Format: `booking client delete INDEX`

Parameters:

`INDEX`: index of target client, relative to the observed client list

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The specified index will work for any currently displayed list. i.e. What you see is what you get.
</div>

Examples:
* `booking client delete 3` deletes the 3rd client's booking date
* `booking client delete 1` deletes the 1st client's booking date<br>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

###### Editing housekeeping details: `booking client edit`

Edits any attribute client's housekeeping details (including last housekeeping date, preferred interval, booking date and deferment).
More than one attribute can be edited in a single `edit` command, by using their respective prefixes.

If the client has **no housekeeping details**, first use the `set` command to add housekeeping details for the client.

Formats and parameters:
1. Edit last housekeeping date: `booking client edit INDEX lhd/DATE`<br>
   Parameters:
   `INDEX`: index of target client, relative to the observed client list
   `DATE`: yyyy-MM-dd format
2. Edit preferred interval: `booking client edit INDEX pi/NUMBER INTERVAL`<br>
   Parameters:
   `INDEX`: index of target client, relative to the observed client list
   `NUMBER`: quantity of `INTERVAL`(s) - non-negative integer
   `INTERVAL`: period between housekeeping sessions - 'days', 'weeks', 'months' or 'years'
3. Edit booking date: `booking client edit INDEX bd/DATE TIME`<br>
   Parameters:
   `INDEX`: index of target client, relative to the observed client list
   `DATE`: yyyy-MM-dd format
   `TIME`: am or pm
4. Edit deferment: `booking client edit INDEX d/NUMBER INTERVAL`<br>
   Parameters:
   `INDEX`: index of target client, relative to the observed client list
   `NUMBER`: quantity of `INTERVAL`(s) - non-negative integer
   `INTERVAL`: period to defer - 'days', 'weeks', 'months' or 'years'
   Edits a deferment to the period before a client's next estimated housekeeping date.
   The default value for deferment is 0.

This deferment attribute can be used to manage clients who want to defer their next housekeeping date. For example, a client who
will be overseas for the next 3 months might want to defer their next housekeeping service to after they are back.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The index to delete will work for any displayed list. i.e. What you see is what you get.<br>
The `INTERVAL` is the period between housekeeping sessions that the client prefers. It is meant to be an estimate, so
options such as `2 weeks and 3 days` are not supported. If precision is needed, you should convert it to `NUMBER days`.<br>
`TIME` is categorised as either 'am' or 'pm' as housekeeping services often span a few hours,
and on account of housekeepers' travelling time between houses. Please look forward to future editions of our
application that support more flexible time frames!
</div>

Examples:
* `booking client edit 2 lhd/2024-04-01` edits the 2nd client's last housekeeping date to 2024-04-01
* `booking client edit 2 pi/2 weeks` edits the 2nd client's preferred interval to 2 weeks
* `booking client edit 2 bd/2024-04-02 am` edits the 2nd client's booking date to 2024-04-02 am
* `booking client edit 2 d/2 months` edits the 2nd client's deferment to 2 months<br>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

#### Booking commands for housekeepers

--------------------------------------------------------------------------------------------------------------------

Every housekeeper has a booking list. This list may be empty.

###### Adding booking date: `booking housekeeper add`

Adds a booking date for the specified housekeeper.

Format: `booking housekeeper add INDEX DATE TIME`

Parameters:

`INDEX`: index of target housekeeper, relative to the observed housekeeper list
`DATE`: yyyy-MM-dd format
`TIME`: am or pm

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The specified index will work for any currently displayed list. i.e. What you see is what you get.
`TIME` is categorised as either 'am' or 'pm' as housekeeping services often span a few hours,
and on account of housekeepers' travelling time between houses. Please look forward to future editions of our
application that support more flexible time frames!
</div>

Examples:
* `booking housekeeper add 3 2024-02-03 am` adds a booking for the 3rd housekeeper at 2024-02-03 am
* `booking housekeeper add 5 2024-08-11 pm` adds a booking for the 5th housekeeper at 2024-08-11 pm

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

###### Listing booking list: `booking housekeeper list`

List the specified housekeeper's booking list.
If the housekeeper has no bookings yet, the booking list showed will be empty.

Format: `booking housekeeper list INDEX`

Parameters:

`INDEX`: index of target housekeeper, relative to the observed housekeeper list

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The specified index will work for any currently displayed list. i.e. What you see is what you get.
</div>

Examples:
* `booking housekeeper list 3` lists the 3rd housekeeper's booking list
* `booking housekeeper list 1` lists the 1st housekeeper's booking list<br>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

###### Deleting booking date: `booking housekeeper delete`

Deletes the specified booking from the specified housekeeper's booking list.

**Use this command after listing your target housekeeper's booking list with the `booking housekeeper list` command
to access their booking list and delete your target booking.**

Format: `booking housekeeper delete HOUSEKEEPER_INDEX BOOKING_INDEX`

Parameters:

`HOUSEKEEPER_INDEX`: index of target housekeeper, relative to the **observed housekeeper list**
`BOOKING_INDEX`: index of target booking, relative to the listed **booking list** of target housekeeper

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
The specified housekeeper index will work for any currently displayed housekeeper list. i.e. What you see is what you get.
</div>

Examples:
* `booking housekeeper delete 3 2` deletes the 2nd booking from the 3rd housekeeper's booking list
* `booking housekeeper delete 4 5` deletes the 5th booking from the 4th housekeeper's booking list<br>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

###### Searching for available housekeepers: `booking housekeeper search`

Searches the housekeeper list for housekeepers who are available at the specified area, date and time.
All parameters (area, date, time) are **mandatory**.

Format: `booking housekeeper search AREA DATE TIME`

Parameters:

`AREA`: north, northeast, east, southeast, south, southwest, west or northwest
`DATE`: yyyy-MM-dd format
`TIME`: am or pm

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
`AREA` is case-sensitive.<br>
`TIME` is categorised as either 'am' or 'pm' as housekeeping services often span a few hours,
and on account of housekeepers' travelling time between houses. Please look forward to future editions of our
application that support more flexible time frames!
</div>

Examples:
* `booking housekeeper search east 2024-04-05 pm` searches for housekeepers who are available in the east, on 2024-04-05 pm
* `booking housekeeper search northwest 2024-08-08 am` searches for housekeepers who are available in the northwest, on 2024-08-08 am<br>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

#### Booking command summary

--------------------------------------------------------------------------------------------------------------------

##### Client booking commands

Action | Format, Explainations, Examples                                                                                                                                               
--------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
`edit lhd/` | Edit client's last housekeeping date with `edit lhd/`<br>Format: `booking client edit INDEX lhd/yyyy-MM-dd`<br>Example: `booking client edit 2 lhd/2024-04-01`                                 
`edit pi/` | Edit client's preferred interval with `edit pi/`<br>Format: `booking client edit INDEX pi/NUMBER INTERVAL`<br>Example: `booking client edit 2 pi/2 weeks`
`edit bd/` | Edit client's booking date with `edit bd/`<br>Format: `booking client edit INDEX bd/BOOKING DATE`<br>Example: `booking client edit 2 bd/2024-04-02 am`
`edit d/` | Edit deferment with `edit d/`<br>Format: `booking client edit INDEX d/NUMBER INTERVAL`<br>Example: `booking client edit 2 d/2 months`
`add` | Add client's booking date with `add`<br>Format: `booking client add INDEX yyyy-MM-dd (am|pm)`<br>Example: `booking client add 2 2024-04-01 am`                                
`delete` | Delete client's booking date with `delete`<br>Format: `booking client delete INDEX` <br>Example: `booking client delete 2`                                                    
`set` | Set client's housekeeping details with `set`. Same format as initiation, you can set last housekeeping date and preferred interval. <br>Format: `booking client set INDEX yyyy-MM-dd NUMBER INTERVAL`<br>Example: `booking client set 2 2024-04-01 15 days`
`remove` | Remove client's housekeeping details with `remove`<br>Format: `booking client remove INDEX`<br>Example: `booking client remove 2`

##### Housekeeper booking commands

Action | Format, Explainations, Examples                                                                                                                                       
--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
`add` | Add booking to a housekeeper's list with `add`<br>Format: `booking housekeeper add INDEX yyyy-MM-dd (am|pm)`<br>Example: `booking housekeeper add 2 2024-04-01 am` 
`delete` | Delete booking from a housekeeper's list with `delete`<br>Format: `booking housekeeper delete INDEX INDEX`<br>Example: `booking housekeeper delete 1 2`<br>* The first INDEX refers to the housekeeper index and the second INDEX refers to the booking index (shown in `list` action).
`list` | List all bookings of a housekeeper with `list`<br>Format: `booking housekeeper list INDEX`<br>Example: `booking housekeeper list 2`
`search` | Search for housekeepers available on a specific area and date date with `search`<br>Format: `booking housekeeper search AREA yyyy-MM-dd (am|pm)`<br>Example: `booking housekeeper search east 2024-04-05 pm`

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------
### Clearing all entries : `clear`

Clears all entries from the HouseKeeping Hub.

Format: `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
This command is irreversible. All data will be lost.
</div>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

HouseKeeping Hub data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

HouseKeeping Hub data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
If you wish to load our sample data, you must delete the existing `addressbook.json` from the stated location and restart the app.
</div>

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, HouseKeeping Hub will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the HouseKeeping Hub to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## FAQ
<a id="faq"></a>

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous HouseKeeping Hub home folder.

[:arrow_up_small:](#table-of-contents)

--------------------------------------------------------------------------------------------------------------------

## Command summary
<a id="command-summary"></a>

Action | Format, Examples
--------|------------------
[**Add**](#adding-a-person-add) | `add TYPE n/NAME e/EMAIL p/PHONE_NUMBER a/ADDRESS [d/DETAILS] [ar/AREA] [t/TAG]…​` <br> e.g., `add client n/Elon e/elon@gmail.com p/088888888 a/Elon Street, Block 123, 101010 Singapore ar/west`
[**Delete**](#deleting-a-person--delete) | `delete TYPE INDEX`<br> e.g., `delete housekeeper 3`
[**List**](#listing-all-persons--list) | `list TYPE`<br> e.g., `list client`
[**Edit**](#editing-a-person--edit) | `edit TYPE INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/DETAILS] [ar/AREA] [t/TAG]…​`<br> e.g., `edit client 1 p/91234567 e/johndoe@example.com`
[**Find**](#locating-persons-by-keywords--find) | `find TYPE n/KEYWORD [MORE_KEYWORDS] ar/KEYWORD [MORE_KEYWORDS] a/KEYWORD [MORE_KEYWORDS]`<br> e.g., `find client n/John`
[**Leads**](#getting-client-call-list-leads) | `leads`
[**Booking**](#booking-commands-booking) | `booking TYPE ACTION INDEX [PARAMETERS]`<br> e.g., `booking client last 2 2024-04-01`<br> * This has many commands and it is recommended to refer to the [Booking commands](#booking-commands-booking) section for more details.
[**Clear**](#clearing-all-entries--clear) | `clear`
[**Exit**](#exiting-the-program--exit) | `exit`
[**Help**](#viewing-help--help) | `help`                                                                                                                                                                                                                                 |

[:arrow_up_small:](#table-of-contents)

----------------------------------------------------------------------------------------------------------------------
## Glossary :book:
<a id="glossary"></a>

**JAR**
<a id="jar"></a>
: JAR stands for Java Archive. It is based on the ZIP file format that is commonly used to store java programs.<br>

<br>

**CLI**
<a id="cli"></a>
: CLI stands for Command Line Interface. It refers to programs that are primarily **text-based** where users interact with the program by typing **commands**. 
As such, users will use their keyboards more, in contrast to a Graphical User Interface (GUI) where users will use their mouse to interact with the graphical elements.<br>

<br>

**GUI**
<a id="gui"></a>
: GUI stands for Graphical User Interface. It refers to programs that are primarily **graphical** where users interact with the program by clicking on **buttons** and **menus**.<br>

<br>

**Terminal**
<a id="terminal"></a>
: A terminal is a Command Line Interface (CLI) that allows users to interact with computers by executing commands and viewing the results. 
Popular terminals in mainstream operating systems include command prompt (CMD) for windows and Terminal in macOS and Linux.<br>

<br>

**CMD**<br><br>
<img src="https://www.auslogics.com/en/articles/wp-content/uploads/2023/07/Command-Prompt-PING.png" alt="drawing" width="500"/>
<br><br>**Terminal (macOS)**<br><br>
<img src="https://forums.macrumors.com/attachments/screen-shot-2020-12-09-at-4-50-12-pm-png.1690397/" alt="drawing" width="500"/>
<br><br> **Terminal (Linux)** <br><br>
<img src="https://static1.howtogeekimages.com/wordpress/wp-content/uploads/2013/03/linux-terminal-on-ubuntu.png" alt="drawing" width="500"/>

[:arrow_up_small:](#table-of-contents)
