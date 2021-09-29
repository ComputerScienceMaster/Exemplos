<!DOCTYPE html>
<head>
	<title>Criar vendedores</title>
</head>
<body>
	<h1>lista de vendedores</h1>

	<table border="1">
	  <tr>
	    <th>Nome</th>
	    <th>email</th>
	    <th>comissao</th>
	  </tr>
 
  	<?php foreach($vendedores as $v): ?>
  	<tr>
	    <td><?= $v->nome?></td>
    	<td><?= $v->email?></td>
    	<td><?= $v->comissao?></td>
  	</tr>
  	<?php endforeach ?>
	</table>
</body>
</html>