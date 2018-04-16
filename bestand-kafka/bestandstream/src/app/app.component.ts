import {Component, ViewChild} from '@angular/core';
import {BestandStreamService} from "./bestandstream.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  ws = new WebSocket('ws://localhost:8080/publishwarenbewegungen/count');
  wsmongo = new WebSocket('ws://localhost:8081/subscribewarenbewegungenmongo/count');
  wsstreamprocessor = new WebSocket('ws://localhost:8082/subscribewarenbewegungenstreamprocessor/count');
  wsstreamprocessorprocessed = new WebSocket('ws://localhost:8082/subscribewarenbewegungenstreamprocessor/processed');
  wsbestand = new WebSocket('ws://localhost:8083/subscribebestandsbewegungen/count');

  @ViewChild('progresschart')
  progresschart;

  @ViewChild('bestandchart')
  bestandchart;

  chartOptions: any;
  bestandChartOptions: any;

  data: any;
  bestanddata;

  bub = 0;
  lib = 0;
  tlib = 0;

  published = 0;
  mongo = 0;
  streamprocessor = 0;
  streamprocessorprocessed = 0;

  private publishcount: String;

  constructor(private bestandStreamService: BestandStreamService) {
    this.data = {
      labels: ['published', 'mongo', 'streamprocessor', 'streamprocessorprocessed'],
      datasets: [
        {
          label: 'Anzahl',
          backgroundColor: '#42A5F5',
          borderColor: '#1E88E5',
          data: [0, 0, 0, 0]
        },
      ]
    };

    this.bestanddata = {
      labels: ['BUB', 'LIB', 'TLIB'],
      datasets: [
        {
          label: 'Menge',
          backgroundColor: '#5fbd6c',
          borderColor: '#63a76b',
          data: [0, 0, 0]
        },
      ]
    };


    this.chartOptions = {
      // responsive: false,
      // maintainAspectRatio: false,
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true,
            min: 0,
            steps: 10,
            max: 100000
          }
        }]
      }
    };

    this.bestandChartOptions = {
      // responsive: false,
      // maintainAspectRatio: false,
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true,
            min: 0,
            steps: 10,
            max: 5000000
          }
        }]
      }
    };

    this.ws.onmessage = (me: MessageEvent) => {
      let json = JSON.parse(me.data);
      this.published = json.id;
      this.updatebar();
    };

    this.wsmongo.onmessage = (me: MessageEvent) => {
      let json = JSON.parse(me.data);
      this.mongo = json.id;
      this.updatebar();
    };

    this.wsstreamprocessor.onmessage = (me: MessageEvent) => {
      let json = JSON.parse(me.data);
      this.streamprocessor = json.id;
      this.updatebar();
    };

    this.wsstreamprocessorprocessed.onmessage = (me: MessageEvent) => {
      let json = JSON.parse(me.data);
      this.streamprocessorprocessed = json.id;
      this.updatebar();
    };

    this.wsbestand.onmessage = (me: MessageEvent) => {
      let json = JSON.parse(me.data);
      json = JSON.parse(json); // keine Ahnung, warum ich es zweimal machen muß
      this.bub = json.bub;
      this.lib = json.lib;
      this.tlib = json.tlib;
      this.updatebestandbar();
    }
  }

  updatebar() {
    this.data.datasets[0].data[0] = this.published;
    this.data.datasets[0].data[1] = this.mongo;
    this.data.datasets[0].data[2] = this.streamprocessor;
    this.data.datasets[0].data[3] = this.streamprocessorprocessed;
    this.progresschart.refresh();
  }

  updatebestandbar() {
    this.bestanddata.datasets[0].data[0] = this.bub;
    this.bestanddata.datasets[0].data[1] = this.lib;
    this.bestanddata.datasets[0].data[2] = this.tlib;
    this.bestandchart.refresh();
  }

  vollepulle(event) {
    this.bestandStreamService.getVollePulle().subscribe(res => {
      console.log(res);
    })
  }

  volle1000(event) {
    this.bestandStreamService.getVolle1000().subscribe(res => {
      console.log(res);
    })
  }

  letzermongo(event) {
    this.bestandStreamService.getLetzerMongo().subscribe(res => {
      console.log(res);
      this.publishcount = res["correlationid"];
    })
  }
}
