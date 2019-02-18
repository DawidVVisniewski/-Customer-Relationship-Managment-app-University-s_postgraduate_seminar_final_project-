<?php
	require_once("SimpleRest.php");
	require_once("activityDatabase.php");
			
	class CActivityRestHandler extends SimpleRest 
	{
		function searchClientActivity() 
		{	
			$activityDatabase = new CActivityDatabase();
			
			$data = $activityDatabase->searchClientActivity();

			if(empty($data)) 
			{
				$statusCode = 404;
				
				$data = array('error' => 'Nie znaleziono aktywności');		
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
		
		public function encodeJSON($responseData) 
		{
			$responseJSON = json_encode($responseData);
			
			return $responseJSON;		
		}
	}
?>