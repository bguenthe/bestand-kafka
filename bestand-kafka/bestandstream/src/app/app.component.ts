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

  publishcount: String;

  charOptions: any;

  data: any;

  published = 0;
  mongo = 0;
  streamprocessor = 0;
  streamprocessorprocessed = 0;

  res: String[];
  private chartOptions: any;

  constructor(private bestandStreamService: BestandStreamService) {
    this.data = {
      labels: ['published', 'mongo', 'streamprocessor', 'streamprocessorprocessed'],
      datasets: [
        {
          label: 'Count',
          backgroundColor: '#42A5F5',
          borderColor: '#1E88E5',
          data: [0, 0, 0, 0]
        },
      ]
    };

    this.chartOptions = {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true,
            steps: 10,
            stepValue: 5,
            min: 0,
            max: 2500000
          }
        }]
      }
    };

    this.ws.onmessage = (me: MessageEvent) => {
      let json = JSON.parse(me.data);
      this.published = json.id;
      this.updatebar();
    }

    this.wsmongo.onmessage = (me: MessageEvent) => {
      let json = JSON.parse(me.data);
      this.mongo = json.id;
      this.updatebar();
    }

    this.wsstreamprocessor.onmessage = (me: MessageEvent) => {
      let json = JSON.parse(me.data);
      this.streamprocessor = json.id;
      this.updatebar();
    }

    this.wsstreamprocessorprocessed.onmessage = (me: MessageEvent) => {
      let json = JSON.parse(me.data);
      this.streamprocessorprocessed = json.id;
      this.updatebar();
    }

    this.wsbestand.onmessage = (me: MessageEvent) => {
      let json = JSON.parse(me.data);
      console.log(json.bub);
      this.updatebar();
    }

  }

  updatebar() {
    this.data.datasets[0].data[0] = this.published;
    this.data.datasets[0].data[1] = this.mongo;
    this.data.datasets[0].data[2] = this.streamprocessor;
    this.data.datasets[0].data[3] = this.streamprocessorprocessed;
    this.progresschart.refresh();
  }

  vollepulle(event) {
    console.log(event);
    this.bestandStreamService.getVollePulle().subscribe(res => {
      console.log(res);
    })
  }

  volle1000(event) {
    console.log(event);
    this.bestandStreamService.getVolle1000().subscribe(res => {
      console.log(res);
    })
  }
}
