from django import forms
from django.contrib.auth.models import User


class frm_register_user(forms.Form):
    username = forms.CharField(required=True)
    fullname = forms.CharField(required=True)
    email = forms.EmailField(required=True)
    password = forms.CharField(required=True)
    phone = forms.CharField(required=True)

    def is_valid(self):

        valid = True

        if not super(frm_register_user, self).is_valid():
            self.adiciona_erro('Por favor, verifique os dados informados')
            valid = False

        user_exists = User.objects.filter(username=self.data['username']).exists()

        if user_exists:
            self.adiciona_erro('Usuario ja existente')
            valid = False

        return valid

    def adiciona_erro(self, message):
        errors = self._errors.setdefault(forms.forms.NON_FIELD_ERRORS, forms.utils.ErrorList())
        errors.append(message)