<?php
	require_once("SimpleRest.php");
	require_once("contactDatabase.php");
			
	class CContactRestHandler extends SimpleRest 
	{
		function searchClientContact() 
		{	
			$contactDatabase = new CContactDatabase();
			
			$data = $contactDatabase->searchClientContact();

			if(empty($data)) 
			{
				$statusCode = 404;
				
				$data = array('error' => 'Nie znaleziono kontaktów');		
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
		
		function addContact() 
		{	
			$contactDatabase = new CContactDatabase();
			$rawData = $contactDatabase->addContact();
			
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
		
		function deleteContactById() 
		{	
			$contactDatabase = new CContactDatabase();
			
			$data = $contactDatabase->deleteContact();
			
			if(empty($data)) 
			{
				$statusCode = 404;
				$data = array('status' => false);		
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