<?php
	require_once("clientRestHandler.php");
			
	$page_key = "";

	if(isset($_GET["page_key"]))
	{
		$page_key = $_GET["page_key"];
	}

	switch($page_key)
	{
		case "list":

			$clientRestHandler = new CClientRestHandler();
			$clientRestHandler->searchUserClients();
			
		break;
		
		case "add":
		
			$clientRestHandler = new CClientRestHandler();
			$clientRestHandler->addClient();
			
		break;

		case "delete":
		
			$clientRestHandler = new CClientRestHandler();
			$result = $clientRestHandler->deleteClientById();
			
		break;
	}
?>
