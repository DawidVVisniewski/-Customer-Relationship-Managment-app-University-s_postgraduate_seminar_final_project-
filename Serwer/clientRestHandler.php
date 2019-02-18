<?php
	require_once("SimpleRest.php");
	require_once("clientDatabase.php");
			
	class CClientRestHandler extends SimpleRest 
	{
		function searchUserClients() 
		{	
			$clientDatabase = new CClientDatabase();
			
			$data = $clientDatabase->searchUserClients();

			if(empty($data)) 
			{
				$statusCode = 404;
				
				$data = array('error' => 'Nie znaleziono klientów');		
			} 
			else 
			{
				$statusCode = 200;
			}

			$requestContentType = 'application/json';
			
			$this ->setHttpHeaders($requestContentType, $statusCode);
			
			$result["account"] = $data;
					
			if(strpos($requestContentType, 'application/json') !== false)
			{
				$response = $this->encodeJSON($result);
				
				echo $response;
			}
		}
		
		function addClient() 
		{	
			$clientDatabase = new CClientDatabase();
			$rawData = $clientDatabase->addClient();
			
			if(empty($rawData)) 
			{
				$statusCode = 404;
				
				$rawData = array('status' => 'true');		
			} 
			else 
			{
				$statusCode = 200;
			}
			
			$requestContentType = 'application/json';
			
			$this ->setHttpHeaders($requestContentType, $statusCode);
			
			$result = $rawData;
					
			if(strpos($requestContentType,'application/json') !== false)
			{
				$response = $this->encodeJson($result);
				
				echo $response;
			}
		}
		
		function deleteClientById() 
		{	
			$clientDatabase = new CClientDatabase();
			
			$data = $clientDatabase->deleteClient();
			
			if(empty($data)) 
			{
				$statusCode = 404;
				$data = array('status' => 'false');		
			} 
			else 
			{
				$statusCode = 200;
			}
			
			$requestContentType = 'application/json';
			
			$this ->setHttpHeaders($requestContentType, $statusCode);
			
			$result = $data;
					
			if(strpos($requestContentType, 'application/json') !== false)
			{
				$response = $this->encodeJson($result);
			
				echo $response;
			}
		}
		
		public function encodeJSON($responseData) 
		{
			$responseJSON = json_encode($responseData);
			
			return $responseJSON;		
		}
	}
?>