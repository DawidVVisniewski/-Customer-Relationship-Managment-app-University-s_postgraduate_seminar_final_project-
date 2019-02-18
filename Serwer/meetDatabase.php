<?php
	require_once("settings.php");

	Class CMeetDatabase
	{
		private $meet = array();

		public function getAllMeet()
		{
			$query_select = "SELECT idMeet,	dateMeet, TIME_FORMAT(timeMeet, '%h:%i %p') AS timeMeet, locationMeet, idClient FROM meet";
			
			$settings = new CSettings();
			
			$this->meet = $settings->executeSelectQuery($query_select);
			
			return $this->meet;
		}	

		public function searchClientMeet()
		{
			if(isset($_GET['id']))
			{
				$id = $_GET['id'];
				
				$query_select = "SELECT idMeet,	dateMeet, TIME_FORMAT(timeMeet, '%h:%i %p') AS timeMeet, locationMeet, idClient FROM meet WHERE idClient ='".$id."'";
			} 
			else 
			{
				$query_select = "SELECT idMeet,	dateMeet, TIME_FORMAT(timeMeet, '%h:%i %p') AS timeMeet, locationMeet, idClient FROM meet";
			}
			
			$settings = new CSettings();
			
			$this->meet = $settings->executeSelectQuery($query_select);
			
			return $this->meet;
		}
		
		public function addMeet()
		{
			if(isset($_POST['date']) && isset($_POST['time']) && isset($_POST['location']) && isset($_POST['id']))
			{
				$date = $_POST['date'];
				$time = $_POST['time'];
				$location = $_POST['location'];
				$id = $_POST['id'];
				
				$query = "INSERT INTO meet (dateMeet, timeMeet, locationMeet, idClient) VALUES('" . $date ."','". $time ."','" . $location ."','" . $id ."')";

				$settings = new CSettings();
				
				$result = $settings->executeQuery($query);
				
				if($result != 0)
				{
					$result = array('status'=>true);
					
					return $result;
				}
			}
		}
		
		public function deleteMeet()
		{
			if(isset($_GET['id']))
			{
				$id = $_GET['id'];
				
				$query_delete = 'DELETE FROM meet WHERE idMeet = '.$id;
				
				$settings = new CSettings();
				
				$result = $settings->executeQuery($query_delete);
				
				if($result != 0)
				{
					$result = array('status'=>'true');
					
					return $result;
				}
			}
		}
	}
?>