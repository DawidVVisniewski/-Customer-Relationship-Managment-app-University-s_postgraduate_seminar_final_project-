<?php
	require_once("settings.php");

	Class CClientDatabase
	{
		private $clients = array();

		public function getAllClients()
		{
			$query_select = "SELECT * FROM client";
			
			$settings = new CSettings();
			
			$this->clients = $settings->executeSelectQuery($query_select);
			
			return $this->clients;
		}	
		
		public function searchUserClients()
		{
			if(isset($_GET['login']))
			{
				$login = $_GET['login'];
				
				$query_select = "SELECT * FROM client WHERE loginUser='".$login."'";
			} 
			else 
			{
				$query_select = "SELECT * FROM client";
			}
			
			$settings = new CSettings();
				
			$this->clients = $settings->executeSelectQuery($query_select);
				
			return $this->clients;
		}
		
		public function addClient()
		{
			if(isset($_POST['name']) && isset($_POST['type']) && isset($_POST['description']) && isset($_POST['login']))
			{
				$name = $_POST['name'];
				$type = $_POST['type'];
				$description = $_POST['description'];
				$login = $_POST['login'];
				
				$query = "INSERT INTO client (nameClient, typeClient, descriptionClient, loginUser) VALUES('" . $name ."','". $type ."','" . $description ."','" . $login ."')";

				$settings = new CSettings();
				
				$result = $settings->executeQuery($query);
				
				if($result != 0)
				{
					$result = array('status'=>true);
					
					return $result;
				}
			}
		}
		
		public function deleteClient()
		{
			if(isset($_GET['id']))
			{
				$id = $_GET['id'];
				
				$query_delete = "DELETE FROM client WHERE idClient = '".$id."'";
				
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