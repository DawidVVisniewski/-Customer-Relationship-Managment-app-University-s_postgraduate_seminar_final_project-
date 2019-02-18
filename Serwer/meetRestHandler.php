<?php
	require_once("SimpleRest.php");
	require_once("meetDatabase.php");
			
	class CMeetRestHandler extends SimpleRest 
	{
		function searchClientMeet() 
		{	
			$meetDatabase = new CMeetDatabase();
			
			$data = $meetDatabase->searchClientMeet();

			if(empty($data)) 
			{
				$statusCode = 404;
				
				$data = array('error' => 'Nie znaleziono zadań');		
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
		
		function addMeet() 
		{	
			$meetDatabase = new CMeetDatabase();
			$rawData = $meetDatabase->addMeet();
			
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
		
		function deleteMeetById() 
		{	
			$meetDatabase = new CMeetDatabase();
			
			$data = $meetDatabase->deleteMeet();
			
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