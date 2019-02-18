<?php
	require_once("settings.php");

	Class CActivityDatabase
	{
		private $activity = array();

		public function getAllActivity()
		{
			$query_select = "SELECT * FROM activity";
			
			$settings = new CSettings();
			
			$this->activity = $settings->executeSelectQuery($query_select);
			
			return $this->activity;
		}	
		
		public function searchClientActivity()
		{
			if(isset($_GET['id']))
			{
				$id = $_GET['id'];
				
				$query_select = "SELECT * FROM activity WHERE idClient='".$id."' ORDER BY dateTimeActivity DESC";
			} 
			else 
			{
				$query_select = "SELECT * FROM activity";
			}
			
			$settings = new CSettings();
			
			$this->activity = $settings->executeSelectQuery($query_select);
			
			return $this->activity;
		}
	}
?>