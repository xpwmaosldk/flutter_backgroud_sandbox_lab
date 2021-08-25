import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'counter.dart';

var channel = const MethodChannel('com.example/test');

class CounterService {
  factory CounterService.instance() => _instance;

  CounterService._internal();

  static final _instance = CounterService._internal();
  StreamSubscription sub;

  final _counter = Counter();

  ValueListenable<int> get count => _counter.count;

  void startCounting() {
    sub = Stream.periodic(Duration(seconds: 1)).listen((_) {
      _counter.increment();
      print('Counter incremented: ${_counter.count.value}');

      channel.invokeMethod('test').then((value) => print('test res : $value'));
    });
  }

  void stopCounting() {
    sub.cancel();
  }
}
