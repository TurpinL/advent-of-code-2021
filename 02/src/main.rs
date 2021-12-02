use std::fs;
use itertools::Itertools;

fn main() {
    println!("Part 1");
    part1();

    println!("\nPart 2");
    part2();
}

fn part1() {
    let input = fs::read_to_string("input")
        .expect("Something went wrong reading the file");

    let input_lines = input.split_whitespace();

    let mut depth = 0;
    let mut horizontal_pos = 0;

    for (direction, value_str) in input_lines.tuples() {
        let value = value_str.parse::<i32>().unwrap();

        match direction {
            "forward" => {
                horizontal_pos += value;
            },
            "up" => {
                depth -= value;
            },
            "down" => {
                depth += value;
            },
            _ => {}
        }
    }

    println!("{} x {} = {}", depth, horizontal_pos, depth * horizontal_pos )
}

fn part2() {
    let input = fs::read_to_string("input")
        .expect("Something went wrong reading the file");

    let input_lines = input.split_whitespace();

    let mut aim = 0;
    let mut depth = 0;
    let mut horizontal_pos = 0;

    for (direction, value_str) in input_lines.tuples() {
        let value = value_str.parse::<i32>().unwrap();

        match direction {
            "forward" => {
                horizontal_pos += value;
                depth += value * aim;
            },
            "up" => {
                aim -= value;
            },
            "down" => {
                aim += value;
            },
            _ => {}
        }
    }

    println!("{} x {} = {}", depth, horizontal_pos, depth * horizontal_pos )
}

