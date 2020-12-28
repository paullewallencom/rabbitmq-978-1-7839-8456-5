-module(sample).
-export([double/2]).
-export([filter_list_sample/1]).
-export([exception_handler/1]).
-export([start/0, sample_sender/2, sample_receiver/0]).

double(X,Y) -> (X+Y) * 2.

filter_list_sample(L) -> filter_list_sample_helper(L, []).
filter_list_sample_helper([], Res) -> Res;
filter_list_sample_helper([X|L], Res) -> 
	if 
		X rem 2 == 0 -> filter_list_sample_helper(L, [X+1| Res]);
		true -> filter_list_sample_helper(L, Res)
	end.

exception_sample(Val) -> 
	case Val of
		1 -> throw("Invalid value: 1");
		2 -> error("Invalid value: 2");
		3 -> exit("Invalid value: 3");
		_ -> "Success"
	end.
	
exception_handler(Val) ->
	try 
		exception_sample(Val)
	catch
		error: Error -> {error, Error};
		throw: Error -> {throw, Error};
		exit: Error -> {exit, Error}	
	end.
	
sample_sender(Pid, Message) -> 
	Pid ! Message.

sample_receiver() -> 
	receive
		Message -> io:format(Message, [])
	end.

start() -> 
	Preceiver = spawn(?MODULE, sample_receiver, []),
	spawn(?MODULE, sample_sender, [Preceiver, "Test message."]).
