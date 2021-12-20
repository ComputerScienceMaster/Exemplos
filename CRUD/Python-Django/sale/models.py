# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib.auth.models import User
from django.db import models
from product.models import Product

# Create your models here.


class Sale(models.Model):
    buyer =  models.ForeignKey(User, on_delete=models.CASCADE)
    date = models.DateField(null=False)
    productsBuyed = models.ManyToManyField(Product)
