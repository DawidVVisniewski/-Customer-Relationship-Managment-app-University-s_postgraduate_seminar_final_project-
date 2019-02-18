<?php
	require_once("taskRestHandler.php");
			
	$page_key = "";

	if(isset($_GET["page_key"]))
	{
		$page_key = $_GET["page_key"];
	}

	switch($page_key)
	{
		case "list":

			$taskRestHandler = new CTaskRestHandler();
			$taskRestHandler->searchClientTask();
			
		break;
		
		case "add":
		
			$taskRestHandler = new CTaskRestHandler();
			$taskRestHandler->addTask();
			
		break;
		
		case "update":
		
			$taskRestHandler = new CTaskRestHandler();
			$taskRestHandler->updateTaskById();
			
		break;

		case "delete":
		
			$taskRestHandler = new CTaskRestHandler();
			$result = $taskRestHandler->deleteTaskById();
			
		break;
	}
?>
