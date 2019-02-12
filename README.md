# banking_app
Sample banking app with Slang Integration.
All the features mentioned below can be accessed by navigating through the app's UI as well

This app has the following features
* Show current account balance
  - Can be triggered by the user saying things like "Show my account balance"
  
* Show recent transactions
  - Can be triggered by the user saying "Show my recent transactions"
  
* Transfer funds
  - Can be triggered by the user saying "Send money". The user can also specify the amount, payee and the date (if needed) 
  by saying "Send money to Kumar" or "Send 5000 rupees to Ved" or "Send 1000 rupees to Kumar on 12th June"

* Order a new cheque book
  - Can be triggered by the user saying "Order a new cheque book"

* Show account statement
  - Can be triggered by the user saying "Show my statement". The user can also specify the duration using specific dates 
  (start and end dates) or the two options present in the app, 'last month' and 'last 3 months' by saying 
  "View account statement for the last 3 months", or "Show account statement from 12th November"

* Pay bills
  - Can be triggered by the user saying "Pay bills". The user can also specify the type of bill to be paid 
  ('Electricity', 'Postpaid', 'Broadband' and 'Water'), the amount and the vendor (from the list of vendors for each category
  in the app). For example the user could say "Pay BESCOM electricity bill for 1000 rupees" or "Pay broadband bill"

* Contact customer support
  - Can be triggered by saying "I want to talk to customer support"

\
The code that sets the functionality for Slang is 
https://github.com/SlangLabs/banking_app/blob/master/app/src/main/java/in/slanglabs/bankingapp/slang/VoiceInterface.java
