<?php
	require_once("settings.php");

	Class CNoteDatabase
	{
		private $clients = array();

		public function getAllNotes()
		{
			$query_select = "SELECT * FROM note";
			
			$settings = new CSettings();
			
			$this->clients = $settings->executeSelectQuery($query_select);
			
			return $this->clients;
		}	
		
		public function searchClientNotes()
		{
			if(isset($_GET['id']))
			{
				$id = $_GET['id'];
				
				$query_select = "SELECT * FROM note WHERE idClient ='".$id."'";
			} 
			else 
			{
				$query_select = "SELECT * FROM note";
			}
			
			$settings = new CSettings();
			
			$this->clients = $settings->executeSelectQuery($query_select);
			
			return $this->clients;
		}
		
		public function addNote()
		{
			if(isset($_POST['title']) && isset($_POST['description']) && isset($_POST['id']))
			{
				$title = $_POST['title'];
				$description = $_POST['description'];
				$id = $_POST['id'];
				
				$query = "INSERT INTO note (titleNote, descriptionNote, idClient) VALUES('" . $title ."','". $description ."','" . $id ."')";

				$settings = new CSettings();
				
				$result = $settings->executeQuery($query);
				
				if($result != 0)
				{
					$result = array('status'=>true);
					
					return $result;
				}
			}
		}
		
		public function deleteNote()
		{
			if(isset($_GET['id']))
			{
				$id = $_GET['id'];
				
				$query_delete = 'DELETE FROM note WHERE idNote = '.$id;
				
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