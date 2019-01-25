const sqlite3 = require('sqlite3');
const db = new sqlite3.Database(':memory:');

db.serialize(() => {
  const sql = 'CREATE TABLE IF NOT EXISTS todos (id integer primary key, title)';
  db.run(sql);
  db.run('INSERT INTO todos(title) VALUES(?)', 'Fazer as compras');
  db.run('INSERT INTO todos(title) VALUES(?)', 'Lavar o carro');
  db.run('INSERT INTO todos(title) VALUES(?)', 'Pagar as contas');
});

class Todo {
  constructor(id, title){
    this.id = id;
    this.title = title;
  }

  static all(callback){
    db.all('SELECT * FROM todos', callback);
  };

  static add(todo){
    const sql = 'INSERT INTO todos(title) VALUES(?)';
    db.run(sql, todo.title);
  };

  static update(todo, callback){
    console.log(todo);
    const sql = 'UPDATE todos SET title = ? WHERE id = ?';
    db.run(sql, todo.title, todo.id, callback);
  };

  static delete(id, callback){
    const sql = 'DELETE FROM todos where id = ?';
    db.run(sql, id, callback);
  };
}

module.exports = Todo;