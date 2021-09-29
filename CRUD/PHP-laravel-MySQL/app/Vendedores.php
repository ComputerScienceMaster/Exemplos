<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Vendedores extends Model
{
	 # This property!
    protected $fillable = array('creators_name', 'type_document', 'title', 'description');
   	public $timestamps = false;
}
