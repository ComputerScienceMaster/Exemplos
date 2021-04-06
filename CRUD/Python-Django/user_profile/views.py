# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render

# Create your views here.
from django.shortcuts import render
from django.http import HttpResponse
from django.shortcuts import redirect
from django.contrib.auth.decorators import login_required
import user_account
from user_profile.models import user_profile


@login_required
def index(request):
    return render(request, 'index.html', {'user_profiles': user_profile.objects.all(), 'logged_profile' : get_logged_user(request)})

@login_required
def show_profile(request, profile_id):
    profile = user_profile.objects.get(id=profile_id)
    logged_profile = get_logged_user(request)
    return render(request, 'profile.html', {'user_profile': profile, 'logged_profile': logged_profile})

def get_logged_user(request):
    return request.user.user_profile