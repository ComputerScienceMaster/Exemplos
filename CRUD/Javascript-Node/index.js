const express = require('express');

const server = express();

server.use(express.json());

const cursos = ['Curso 1', 'Curso 2', 'Curso3'];

// Retorna um curso

server.get('/cursos/:index', (req, res) =>{
    const { index } = req.params;

    return res.json(cursos[index]);
});


server.get('/cursos', (req, res) =>{
    const { index } = req.params;

    return res.json(cursos);
});

server.post('/cursos', (req, res) =>{
    const { name } = req.body;
    
    cursos.push(name);

    return res.json(cursos);
});


server.listen(3000);