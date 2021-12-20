# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.

class Category(models.Model):
    name = models.CharField(max_length=255, null=False)

class Product(models.Model):
    name = models.CharField(max_length=255, null=False)
    expiration = models.DateField(auto_now=True, null=False)
    price = models.FloatField(null=False)
    category = models.ForeignKey(Category, on_delete=models.CASCADE)

