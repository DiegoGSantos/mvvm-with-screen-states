const express = require('express');
const port = 3000;
const app = express();
const Todo = require('./models/todo');

app.use(express.json()) 

var campinas = {"id": 1, "city_name": "Campinas", "forecast_desc": "Predominantemente ensolarado", "weather": "sunny", "temperature": "28° C", "period_of_the_day": "morning"};
app.get('/forecast', function(request, response) {
  if(request.query.city.toLowerCase() == "campinas") {
    response.status(200).send(campinas)
  } else {
    response.status(404).send("City not found")
  }
});

app.get('/', function(request, response){
  Todo.all((err, todos) => response.format({
    json: () =>{
    response.status(200).json(todos);
  },
  html: () => {
    response.status(406).send('Not supported yet\n');
  }
  }));
});

app.post('/', (request, response) => {
    var newTodo = request.body;
    Todo.add(newTodo);
    response.status(201).json(request.body);
});

app.put('/:id', (request, response) => {
  var id = request.params.id;
  var updatedTodo = request.body;
  updatedTodo.id = parseInt(id);
  Todo.update(updatedTodo, (err, data) => {
    if(err)
    {
      response.status(404, 'The task is not found').send();
    } else {
    response.status(204).send(data);
  }
});
});

app.delete('/:id', (request, response) => {
  var id = parseInt(request.params.id);
  Todo.delete(id, (err) => {
    if(err){
      response.status(404).send();
    }else{
          response.status(200).send();
    }
  });
});

app.listen(port);