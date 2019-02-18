<?php
	require_once("accountRestHandler.php");
			
	$page_key = "";

	if(isset($_GET["page_key"]))
	{
		$page_key = $_GET["page_key"];
	}

	switch($page_key)
	{
		case "list":

			$accountRestHandler = new CAccountRestHandler();
			$accountRestHandler->searchAccount();
			
		break;

		case "" :
		break;
	}
?>
