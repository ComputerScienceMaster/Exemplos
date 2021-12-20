<!DOCTYPE html>
<head>
	<title>Criar vendedores</title>
</head>
<body>
	<h1>criar venda</h1>
	<form method="POST" action="api/venda/criar">
		<input type="text" name="valor" placeholder="Digite o valor da venda">
		<input type="date" name="datavenda">
		<select name="idvendedor">
			<?php foreach($vendedores as $v): ?>
			<option value="<?= $v->id?>"><?= $v->nome?></option>
			<?php endforeach ?>
		</select>
		<input type="submit" value="criar">
	</form>
</body>
</html>