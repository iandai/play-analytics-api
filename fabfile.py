from __future__ import with_statement
from fabric.api import local, run, env, put, cd, settings, task, execute
from fabric.contrib.console import confirm
from fabric.contrib.files import exists
from fabric.operations import _prefix_commands, _prefix_env_vars, require


STAGES = {
    'dev': {
        'host_name': [''],
        'hosts': [''],
        'code_dir': '',
        'code_branch': '',
        'desc': '',
        'pid_file': '',
        'port': '',
        'config_file': 'conf/dev.conf',        
    },
    'stg': {
        'host_name': [''],
        'hosts': [''],
        'code_dir': '',
        'code_branch': '',
        'desc': '',
        'pid_file': '',
        'port': '',
        'config_file': 'conf/stg.conf',        
    },
    'prod': {
        'host_name': [''],
        'hosts': [''],
        'code_dir': '',
        'code_branch': '',
        'desc': '',
        'pid_file': '',
        'port': '',
        'config_file': 'conf/prod.conf',        
    },
}

env.user = "some_user"
env.use_ssh_config = True    # Don't forget this line


def stage_set(stage_name='dev'):
    env.stage = stage_name
    for option, value in STAGES[env.stage].items():
        setattr(env, option, value)

@task
def dev():
    stage_set('dev')

@task
def stg():
    stage_set('stg')    

@task
def prod():
    stage_set('prod')
        
@task
def deploy():
    with cd(env.code_dir):
        run("git pull origin master")  # User master branch as default
    	run("./activator clean stage")
        deploy_command = "sudo su -c 'sh extra/deploy.sh %s %s %s %s %s restart'" % (env.code_dir, env.desc, env.pid_file, env.port, env.config_file)
        run(deploy_command)

@task
def stop_server():
    with cd(env.code_dir):
        command = "sudo su -c 'sh extra/deploy.sh %s %s %s %s %s stop'" % (env.code_dir, env.desc, env.pid_file, env.port, env.config_file)
        run(command)

@task        
def start_server():
    with cd(env.code_dir):
        command = "sudo su -c 'sh extra/deploy.sh %s %s %s %s %s start'" % (env.code_dir, env.desc, env.pid_file, env.port, env.config_file)
        run(command)