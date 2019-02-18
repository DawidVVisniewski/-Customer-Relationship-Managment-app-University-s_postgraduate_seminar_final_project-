<?php
	require_once("SimpleRest.php");
	require_once("noteDatabase.php");
			
	class CNoteRestHandler extends SimpleRest 
	{
		function searchClientNotes() 
		{	
			$noteDatabase = new CNoteDatabase();
			
			$data = $noteDatabase->searchClientNotes();

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
		
		function addNote() 
		{	
			$noteDatabase = new CNoteDatabase();
			$rawData = $noteDatabase->addNote();
			
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
		
		function deleteNoteById() 
		{	
			$noteDatabase = new CNoteDatabase();
			
			$data = $noteDatabase->deleteNote();
			
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