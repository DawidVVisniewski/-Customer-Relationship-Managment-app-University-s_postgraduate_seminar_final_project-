<?php
	require_once("SimpleRest.php");
	require_once("accountDatabase.php");
			
	class CAccountRestHandler extends SimpleRest 
	{
		function searchAccount() 
		{	
			$accountDatabase = new CAccountDatabase();
			
			$data = $accountDatabase->searchAccount();

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