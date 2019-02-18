<?php
	require_once("contactRestHandler.php");
			
	$page_key = "";

	if(isset($_GET["page_key"]))
	{
		$page_key = $_GET["page_key"];
	}

	switch($page_key)
	{
		case "list":

			$contactRestHandler = new CContactRestHandler();
			$contactRestHandler->searchClientContact();
			
		break;
		
		case "add":
		
			$contactRestHandler = new CContactRestHandler();
			$contactRestHandler->addContact();
			
		break;

		case "delete":
		
			$contactRestHandler = new CContactRestHandler();
			$result = $contactRestHandler->deleteContactById();
			
		break;
	}
?>
