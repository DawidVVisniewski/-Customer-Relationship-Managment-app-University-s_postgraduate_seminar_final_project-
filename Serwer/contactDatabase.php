<?php
	require_once("settings.php");

	Class CContactDatabase
	{
		private $contact = array();

		public function getAllContact()
		{
			$query_select = "SELECT * FROM contact";
			
			$settings = new CSettings();
			
			$this->contact = $settings->executeSelectQuery($query_select);
			
			return $this->contact;
		}	
		
		public function searchClientContact()
		{
			if(isset($_GET['id']))
			{
				$id = $_GET['id'];
				
				$query_select = "SELECT * FROM contact WHERE idClient ='".$id."'";
			} 
			else 
			{
				$query_select = "SELECT * FROM contact";
			}
			
			$settings = new CSettings();
			
			$this->contact = $settings->executeSelectQuery($query_select);
			
			return $this->contact;
		}
		
		public function addContact()
		{
			if(isset($_POST['name']) && isset($_POST['surname']) && isset($_POST['phone']) && isset($_POST['id']))
			{
				$name = $_POST['name'];
				$surname = $_POST['surname'];
				$phone = $_POST['phone'];
				$id = $_POST['id'];
				
				$query = "INSERT INTO contact (nameContact, surnameContact, phoneContact, idClient) VALUES('" . $name ."','". $surname ."','" . $phone ."','" . $id ."')";

				$settings = new CSettings();
				
				$result = $settings->executeQuery($query);
				
				if($result != 0)
				{
					$result = array('status'=>true);
					
					return $result;
				}
			}
		}
		
		public function deleteContact()
		{
			if(isset($_GET['id']))
			{
				$id = $_GET['id'];
				
				$query_delete = 'DELETE FROM contact WHERE idContact = '.$id;
				
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