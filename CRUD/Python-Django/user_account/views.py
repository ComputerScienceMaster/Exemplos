# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.shortcuts import render

# Create your views here.

from django.shortcuts import redirect
from django.shortcuts import render
from django.contrib.auth.models import User
from django.views.generic.base import View
from user_profile.models import user_profile
from user_account.forms import frm_register_user


class register_user_view(View):
    template_name = 'register.html'

    def get(self, request, *args, **kwargs):
        return render(request, self.template_name)

    def post(self, request, *args, **kwargs):
        form = frm_register_user(request.POST)

        if form.is_valid():
            dados_form = form.data

            usuario = User.objects.create_user(dados_form['username'],
                                               dados_form['email'], dados_form['password'])

            perfil = user_profile(
                            username=dados_form['username'],
                            fullname=dados_form['fullname'],
                            email=dados_form['email'],
                            phone=dados_form['phone'],
                            user=usuario)

            perfil.save()

            return redirect('index')

        return render(request, self.template_name, {'form': form})

