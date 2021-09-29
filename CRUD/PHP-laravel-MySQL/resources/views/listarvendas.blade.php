<!DOCTYPE html>
<!DOCTYPE html>
<html>
<head>
	<title>Criar vendedores</title>
</head>
<body>
	<h1>lista de vendedores</h1>
	<ul>
		<?php foreach($vendas as $v): ?>
			<li><?= $v->valor?> - <?= $v->datavenda?> - <?= $v->idvendedor?></li>
		<?php endforeach ?>
	</ul>
</body>
</html>