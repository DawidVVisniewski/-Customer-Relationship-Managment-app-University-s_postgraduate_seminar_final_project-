<?php
	require_once("activityRestHandler.php");
			
	$page_key = "";

	if(isset($_GET["page_key"]))
	{
		$page_key = $_GET["page_key"];
	}

	switch($page_key)
	{
		case "list":

			$activityRestHandler = new CActivityRestHandler();
			$activityRestHandler->searchClientActivity();
			
		break;
	}
?>
