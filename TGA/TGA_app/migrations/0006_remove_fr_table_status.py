# -*- coding: utf-8 -*-
# Generated by Django 1.11.17 on 2023-05-04 11:29
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('TGA_app', '0005_fr_table_friends'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='fr_table',
            name='status',
        ),
    ]
