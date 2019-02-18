<?php
	require_once("noteRestHandler.php");
			
	$page_key = "";

	if(isset($_GET["page_key"]))
	{
		$page_key = $_GET["page_key"];
	}

	switch($page_key)
	{
		case "list":

			$noteRestHandler = new CNoteRestHandler();
			$noteRestHandler->searchClientNotes();
			
		break;
		
		case "add":
		
			$noteRestHandler = new CNoteRestHandler();
			$noteRestHandler->addNote();
			
		break;

		case "delete":
		
			$noteRestHandler = new CNoteRestHandler();
			$result = $noteRestHandler->deleteNoteById();
			
		break;
	}
?>
