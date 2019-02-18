<?php
	require_once("settings.php");

	Class CAccountDatabase
	{
		private $account = array();

		public function searchAccount()
		{
			if(isset($_GET['login']) && isset($_GET['password']))
			{
				$login = $_GET['login'];
				$password = $_GET['password'];
				
				$query_select = "SELECT * FROM account WHERE loginUser='".$login."' AND passwordUser='".$password."'";
				
				$settings = new CSettings();
				
				$this->account = $settings->executeSelectQuery($query_select);
				
				return $this->account;
			} 
		}
	}
?>