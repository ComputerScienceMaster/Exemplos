<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Vendas extends Model
{
     protected $fillable = array('creators_name', 'type_document', 'title', 'description');
    public $timestamps = false;
}
