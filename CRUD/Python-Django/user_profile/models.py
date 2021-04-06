# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.db import models
from django.contrib.auth.models import User

# Create your models here.

class user_profile(models.Model):
	username = models.CharField(max_length=255, null=False)
	fullname = models.CharField(max_length=255,null=False)
	email = models.CharField(max_length=100, null=False)
	phone = models.CharField(max_length=20, null=False)
	user = models.OneToOneField(User, related_name="user_profile")