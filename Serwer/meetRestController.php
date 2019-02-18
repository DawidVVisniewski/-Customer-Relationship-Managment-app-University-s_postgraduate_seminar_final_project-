<?php
	require_once("meetRestHandler.php");
			
	$page_key = "";

	if(isset($_GET["page_key"]))
	{
		$page_key = $_GET["page_key"];
	}

	switch($page_key)
	{
		case "list":

			$meetRestHandler = new CMeetRestHandler();
			$meetRestHandler->searchClientMeet();
			
		break;

		case "add":
		
			$meetRestHandler = new CMeetRestHandler();
			$meetRestHandler->addMeet();
			
		break;
		
		case "delete":
		
			$meetRestHandler = new CMeetRestHandler();
			$result = $meetRestHandler->deleteMeetById();
			
		break;
	}
?>
