<?php
	class CSettings 
	{
		private $conn = "";
		private $host = "localhost";
		private $user = "admin";
		private $password = "admin";
		private $database = "db_app_crm";

		function __construct() 
		{
			$conn = $this->connectDB();
			
			if(!empty($conn)) 
			{
				$this->conn = $conn;			
			}
		}

		function connectDB() 
		{
			$conn = mysqli_connect($this->host,$this->user,$this->password,$this->database);
			
			mysqli_set_charset($conn, 'utf8');
			
			return $conn;
		}

		function executeQuery($query) 
		{
			$conn = $this->connectDB();  
			
			$result = mysqli_query($conn, $query);
			
			if(!$result) 
			{
				if($conn->errno == 1062) 
				{
					return false;
				}
			}		
			
			$affectedRows = mysqli_affected_rows($conn);
			
			return $affectedRows;
			
		}
		
		function executeSelectQuery($query) 
		{
			$result = mysqli_query($this->conn,$query);
			
			while($row=mysqli_fetch_assoc($result)) 
			{
				$resultset[] = $row;
			}
			
			if(!empty($resultset))
			{
				return $resultset;
			}
		}
	}
?>
