function main()
{
   // Get the user name of the person to get
   var userName = url.extension;
   
   // Get the person who has that user name
   var person = people.getPerson(userName);
   
   if (person != null) 
   {
      // Pass the person to the template
      model.person = person;
   }
   else 
   {
      // Return 404 - Not Found      
      status.setCode(status.STATUS_NOT_FOUND, "Person " + userName + " does not exist");
      return;
   }
}

main();
