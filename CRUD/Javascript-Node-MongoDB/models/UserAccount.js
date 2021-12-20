// grab the things we need
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/basenode');
var Schema = mongoose.Schema;

// create a schema
var UserAccount = new Schema({
  name: String,
  email: String,
  username: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  admin: Boolean,
  location: String,
  meta: {
    age: Number,
    website: String
  },
  created_at: Date,
  updated_at: Date
});

// the schema is useless so far
// we need to create a model using it
var UserAccount = mongoose.model('UserAccount', UserAccount);

// make this available to our users in our Node applications
module.exports = UserAccount;