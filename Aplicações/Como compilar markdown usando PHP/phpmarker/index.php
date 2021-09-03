
<?php

require_once "./vendor/autoload.php";


use Michelf\Markdown;


$exs = array();
for ($i = 1 ; $i <= 3; $i++){
	array_push($exs,file_get_contents("../markdownFiles/exercise0$i.md"));
}



?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<title>Exercícios do CSM</title>
</head>
<body>

	<?php
	for ($i=0; $i < 3; $i++) { ?>
		<div>
		<div class="card col-md-6" style="margin: 20px; background-color: #efefef;">
			<div class="card-body">
				<?php echo Markdown::defaultTransform($exs[$i]); ?>
			</div>
		</div>
	<?php }	?>
</body>
</html>