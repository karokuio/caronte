#!/usr/bin/env bash

tmux -2 new-session -d -s caronte
tmux rename-window -t caronte:0 'dev'
tmux select-window -t caronte:0
tmux send-keys -t caronte:0 'cat welcome' C-m

tmux select-window -t caronte:0
tmux -2 attach-session -t caronte
