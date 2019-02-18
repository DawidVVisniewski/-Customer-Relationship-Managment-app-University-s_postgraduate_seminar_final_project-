<?php
	require_once("settings.php");

	Class CTaskDatabase
	{
		private $task = array();

		public function getAllTask()
		{
			$query_select = "SELECT * FROM task";
			
			$settings = new CSettings();
			
			$this->task = $settings->executeSelectQuery($query_select);
			
			return $this->task;
		}	
		
		public function searchClientTask()
		{
			if(isset($_GET['id']))
			{
				$id = $_GET['id'];
				
				$query_select = "SELECT * FROM task WHERE idClient ='".$id."'";
			} 
			else 
			{
				$query_select = "SELECT * FROM task";
			}
			
			$settings = new CSettings();
			
			$this->task = $settings->executeSelectQuery($query_select);
			
			return $this->task;
		}
		
		public function addTask()
		{
			if(isset($_POST['title']) && isset($_POST['description']) && isset($_POST['flag']) && isset($_POST['id']))
			{
				$title = $_POST['title'];
				$description = $_POST['description'];
				$flag = $_POST['flag'];
				$id = $_POST['id'];
				
				$query = "INSERT INTO task (titleTask, descriptionTask, flagTask, idClient) VALUES('" . $title ."','". $description ."','" . $flag ."','" . $id ."')";

				$settings = new CSettings();
				
				$result = $settings->executeQuery($query);
				
				if($result != 0)
				{
					$result = array('status'=>'true');
					
					return $result;
				}
			}
		}
		
		public function updateTask()
		{
			if(isset($_POST['flag']) && isset($_GET['id']))
			{
				$flag = $_POST['flag'];
				$id = $_GET['id'];
				
				$query = "UPDATE task SET flagTask = '".$flag."' WHERE idTask = '".$id."'";
			
				$settings = new CSettings();
				
				$result = $settings->executeQuery($query);
				
				if($result != 0)
				{
					$result = array('status'=>'true');
					
					return $result;
				}
			}
		}
		
		public function deleteTask()
		{
			if(isset($_GET['id']))
			{
				$id = $_GET['id'];
				
				$query_delete = 'DELETE FROM task WHERE idTask = '.$id;
				
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