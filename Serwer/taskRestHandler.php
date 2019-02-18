<?php
	require_once("SimpleRest.php");
	require_once("taskDatabase.php");
			
	class CTaskRestHandler extends SimpleRest 
	{
		function searchClientTask() 
		{	
			$taskDatabase = new CTaskDatabase();
			
			$data = $taskDatabase->searchClientTask();

			if(empty($data)) 
			{
				$statusCode = 404;
				
				$data = array('error' => 'Nie znaleziono notatek');		
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
		
		function addTask() 
		{	
			$taskDatabase = new CTaskDatabase();
			$rawData = $taskDatabase->addTask();
			
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
		
		function updateTaskById() 
		{	
			$taskDatabase = new CTaskDatabase();
			
			$rawData = $taskDatabase->updateTask();
			
			if(empty($rawData)) 
			{
				$statusCode = 404;
				$rawData = array('status' => 'false');		
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
		
		function deleteTaskById() 
		{	
			$taskDatabase = new CTaskDatabase();
			
			$data = $taskDatabase->deleteTask();
			
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