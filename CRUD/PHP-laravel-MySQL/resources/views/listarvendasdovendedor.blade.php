<!DOCTYPE html>
<head>
	<title> Vendas do vendedor </title>
</head>
<body>
	<h1>lista de vendedores</h1>

	<table border="1">
	  <tr>
	    <th>valor</th>
	    <th>data da venda</th>
	    <th>id vendedor</th>
	    <th>comissao</th>
	  </tr>
	  <?php foreach($vendas as $v): ?>
	  <tr>
			<td><?= $v->valor?></td>
			<td><?= $v->datavenda?> </td>
			<td><?= $v->idvendedor?></td>
			<td><?= $v->comissao?></td>
	  </tr>
	  <?php endforeach ?>
</table>
</body>
</html>